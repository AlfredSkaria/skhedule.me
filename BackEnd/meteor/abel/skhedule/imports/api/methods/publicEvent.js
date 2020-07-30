import {Meteor} from 'meteor/meteor'
import { Mongo } from 'meteor/mongo'
import Schema from '../schema'
import {check} from 'meteor/check'


PublicEvent = new Meteor.Collection('PublicEvent')


PublicEvent.attachSchema(Schema.Public)


Meteor.methods({
	'Public.createEvent':function(publicEvent,userId){
		let resp = {};
		try{
			if(!userId){
				resp['response'] = "Not logged in";
				throw new Meteor.Error('500','Must be logged in to create public Event')
			}

			let eventId = PublicEvent.insert({...publicEvent,'owner':userId})
			resp['response'] = "Event Created into db"

			if(!eventId){
				return "Public Event Not Created"
			}

			Meteor.call('User.addToPublic',userId,eventId);
			resp['response'] = "Event added to owner data"

			publicEvent['invite-list'].forEach(function (invite) {
	        	
	        	let id = Meteor.call('User.findId',invite.mail)

	        	Meteor.call('User.addToPublic',id,eventId)
	        });

	        resp['response'] = "success";
	        
	        return resp;

		} catch(exception){
			throw new Meteor.Error('500', exception.message);
		}


	},
	//Have to change this
	'Public.deleteEvent':function(eventId,userId){
		try{
			if(!userId){
				throw new Meteor.Error('500','Must be logged IN')
			}
			PublicEvent.remove({"_id":eventId}, callback);
			
		}
		catch(exception){
			throw new Meteor.Error('500', exception.message);
		}
	},
	'Public.listEvents' : function(){
		return PublicEvent.find({}).fetch()
	},
	'Public.getDetails' : function(id){
		return PublicEvent.findOne({ '_id':id});
	},
	'Public.getDetailsNotif' : function(eventId){
		return PublicEvent.find({'_id':eventId},{fields : { 'name':1,'description':1,'start-date':1,'location':1 }}).fetch()[0]
	},
	
  	'Public.acceptEvent':function(userId,eventId){

  		// if ( UserData.update({'userId':userId,'private-event.id':eventId}, {$set : { 'private-event.$.response' :1 } }) ){
  		// 	return true;
  		// }
  		// else
  		// 	return false;

  		let status = false;
  		if ( UserData.update({'userId':userId,'public-event.id':eventId}, {$set : { 'public-event.$.response' :1 } }) ){
  			status = true;
  		}
  		

  		let mail = Meteor.call('User.findId',userId)
  		if ( PublicEvent.update({'_id':eventId,'invite-list.mail':mail},{$set:{'invite-list.$.response':1} }) ){
  			status = true
  		}
  		else
  			status = false

  		return status

  	},
  	'Public.denyEvent': function(userId,eventId){
  		let status = false;
  		if ( UserData.update({'userId':userId,'public-event.id':eventId}, {$set : { 'private-event.$.response' :2 } }) ){
  			status = true;
  		}
  		

  		let mail = Meteor.call('User.findId',userId)
  		if ( PublicEvent.update({'_id':eventId,'invite-list.mail':mail},{$set:{'invite-list.$.response':2} }) ){
  			status = true
  		}
  		else
  			status = false

  		return status
  	},
  	'Public.getNotifTab' : function(user){
  			let pe=[]

			 UserData.aggregate([
				{$match : { $and:[{'userId':user},{'public-event.response':0} ] }
				
				} ,

				{
					$project :{
						'public-event': { $filter:{
							input: '$public-event',
							as : 'event',
							cond: {$eq: ['$$event.response',0] }
						}},
						_id:0
					}
				}

				]).forEach(function (obj) {
					


					
					obj['public-event'].forEach( function(event) {
						// statements
						let details = Meteor.call('Public.getDetailsNotif',event.id)
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









	'Public.testCreateEvent': function(uId){
		let st,vt,temp;
		for(let i=0;i<10;i++){
  			let st = new Date()
  			let nextday = Math.floor(Math.random() * 30)
  			let vt = new Date(new Date().getTime() + (nextday*24*60*60*1000 ))
  			temp = {'name':"EventName"+i,'description':"This is the event No"+i+" ",'start-date':st,'end-date':vt,'location':"Palakkad"}
  			Meteor.call('Public.createEvent',temp,uId)
  		}

  		return "success";
	}
	,
	'Public.test':function(){
			return Accounts.findUserByEmail("abel@gmail.com")
		}




})

export default PublicEvent