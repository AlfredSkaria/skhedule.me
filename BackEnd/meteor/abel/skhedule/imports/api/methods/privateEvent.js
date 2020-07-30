import {Meteor} from 'meteor/meteor'
import { Mongo } from 'meteor/mongo'
import Schema from '../schema'
import './user'
import {check} from 'meteor/check'
import {Accounts } from 'meteor/accounts-base'




PrivateEvent = new Meteor.Collection('PrivateEvent');

PrivateEvent.attachSchema(Schema.Private)



Meteor.methods({
	
	//Method to create a new Private Event by a user
  	'Private.createEvent':function(privateEvent,userId){
  		let resp = {};
    	
  		
	    try{
	      if(!userId ) {
	      	resp['response'] = "Not logged in";
	        throw new Meteor.Error('500','Must be logged In to create event');

	      }
	       
	        let eventId=PrivateEvent.insert({...privateEvent,'owner':userId })
	        resp['response'] = "Event Created into db"

	        //Setting it for creator
	        Meteor.call('User.addToPrivate', userId,eventId,1)
	        resp['response'] = "Event added to owner data"

	        // if(privateEvent['invite-list'].length > 0 ){
	        // 	for ( guest in privateEvent['invite-list'] ){
	        // 		let id = Meteor.call('User.findId',guest['mail'])

	        // 		Meteor.call('User.addToPrivate',id,eventId)
	        // 	} 
	        // }

	        privateEvent['invite-list'].forEach(function (invite) {
	        	
	        	let id = Meteor.call('User.findId',invite.mail)

	        	Meteor.call('User.addToPrivate',id,eventId)
	        });

	        resp['response'] = "success";
	        
	        return resp;

	       
	    } catch(exception){

	      throw new Meteor.Error('500', exception.message);
	    }
	},
	//Have to change this
	'Private.deleteEvent':(eventId,userId)=>{
	    check({eventId},
	      'eventId':String
	    )

	    try{
	      if(!userId) {
	        throw new Meteor.Error('500','Must be logged In to delete event');
	      }
	      return PrivateEvent.remove({"_id":eventId})
	    } catch(exception){

	      throw new Meteor.Error('500', exception.message);
	    }
	},

	'Private.changeEvent': (privateEvent) => {
	    check(privateEvent,{
	      'eventId':String,
	      'name':String,
	      'description':String,
	      'start-date':Date,
	      'end-date':Date,
	      'location':String
	    })

	    try{
	      if(!this.userId()){
	        throw new Meteor.Error('500','User Must be logged in to change Event')

	      }
	      return PrivateEvent.update({"_id":privateEvent.eventId}, {$set : {'name':privateEvent.name,'description':privateEvent.description,'start-date':privateEvent['start-date'],'end-date':privateEvent['end-date'],'location':privateEvent.location }})
	    } catch(exception){

	      throw new Meteor.Error('500', exception.message);
	    }
    },
    'Private.addInvite': (mail,eventId) =>{
	    check({mail,eventId},{
	      'mail':String,
	      'eventId':String

	    })

	    try{
	      if(!this.userId()){
	        throw new Meteor.Error('500','User Must be logged in to add')

	      }

	      let oldInvites = PrivateEvent.find({"_id":eventId},{"_id":0,'invite-list':1}).fetch()


	      return PrivateEvent.update({"_id":eventId}, {$set : { } } )
	    } catch(exception){

	      throw new Meteor.Error('500', exception.message);
	    }
  	},

  	'PrivateEvent.getDetails': function(eventId){

  		return PrivateEvent.findOne({
  			'_id':eventId
  		})	
  	},
  	'Private.getDetailsNotif': function(eventId){
  		return PrivateEvent.find({'_id':eventId},{fields : { 'name':1,'description':1,'start-date':1,'location':1 }}).fetch()[0]
  	},
  	'Private.acceptEvent':function(userId,eventId){

  		// if ( UserData.update({'userId':userId,'private-event.id':eventId}, {$set : { 'private-event.$.response' :1 } }) ){
  		// 	return true;
  		// }
  		// else
  		// 	return false;

  		let status = false;
  		if ( UserData.update({'userId':userId,'private-event.id':eventId}, {$set : { 'private-event.$.response' :1 } }) ){
  			status = true;
  		}
  		

  		let mail = Meteor.call('User.findId',userId)
  		if ( PrivateEvent.update({'_id':eventId,'invite-list.mail':mail},{$set:{'invite-list.$.response':1} }) ){
  			status = true
  		}
  		else
  			status = false

  		return status

  	},
  	'Private.denyEvent': function(userId,eventId){
  		let status = false;
  		if ( UserData.update({'userId':userId,'private-event.id':eventId}, {$set : { 'private-event.$.response' :2 } }) ){
  			status = true;
  		}
  		

  		let mail = Meteor.call('User.findId',userId)
  		if ( PrivateEvent.update({'_id':eventId,'invite-list.mail':mail},{$set:{'invite-list.$.response':2} }) ){
  			status = true
  		}
  		else
  			status = false

  		return status
  	},

  	'Private.getNotifTab' : function(user){
  			let pe=[]

			 UserData.aggregate([
				{$match : { $and:[{'userId':user},{'private-event.response':0} ] }
				
				} ,

				{
					$project :{
						'private-event': { $filter:{
							input: '$private-event',
							as : 'event',
							cond: {$eq: ['$$event.response',0] }
						}},
						_id:0
					}
				}

				]).forEach(function (obj) {
					


					
					obj['private-event'].forEach( function(event) {
						// statements
						let details = Meteor.call('Private.getDetailsNotif',event.id)
						pe.push(details)
					});
					

					// for ( event in obj['private-event'] ){
					// 	let details = Meteor.call('Private.getDetailsNotif',event["id"])
					// 	pe.push(details)

					// }
					// list['private-events'] = pe

					


				});

				
				

					
				
			return pe
  	},











  	//Test Cases

  	'PrivateEvent.testAddEvent': function(uId){
  		
  		/*
  		st = new Date()
  		vt = new Date()

  		let temp = {'name':"NNNNN",'description':"laslaslasl",'start-date': st,'end-date': vt,'location':"Palakkad"}


  		return Meteor.call('Private.addEvent',temp,uId)
  		//return console.log(temp);
		*/

  		for(let i=0;i<10;i++){
  			let st = new Date()
  			let nextday = Math.floor(Math.random() * 30)
  			let vt = new Date(new Date().getTime() + (nextday*24*60*60*1000 ))
  			temp = {'name':"Private "+i+" Events",'description':"This is the event No "+i+" and I invite you ",'start-date':st,'end-date':vt,'location':"Palakkad"}
  			Meteor.call('Private.createEvent',temp,uId)
  		}

  		return "success";

  	},

  	


});

export default PrivateEvent
