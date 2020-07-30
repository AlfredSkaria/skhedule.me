import {Meteor} from 'meteor/meteor'
import Schema  from '../schema'
import {check} from 'meteor/check'
import {AccountsServer, Accounts} from 'meteor/accounts-base'






UserData = new Meteor.Collection('user-data')

UserData.attachSchema( Schema.UserData ) 

if(true) {
	Meteor.methods({



		'User.findId' : function(email){

			

			return Meteor.users.find({$or : [{ 'emails':{ $elemMatch:{ 'address':email}}},   { 'services.facebook.email':email},
						{ 'services.google.email':email  }
						] }
						,{ $fields:{   '_id':1 } } ).fetch()[0]['_id']

		},

		'User.getDetails' : function(id){

			let username,email

			Meteor.users.find({'_id':id},{fields:{'_id':0,'profile.name':1,'emails.address':1,'services.google.email':1,'services.facebook.email':1}}).forEach(function (data) {
				name = data.profile.name
				if( data.emails ) 
					email = data.emails[0].address
				else if ( data.services.google )
					email = data.services.google.email
				else if ( data.services.facebook )
					email = data.services.facebook.email
				else 
					email = "Error"
			});;

			return {'name':name, 'email':email};

		},

		'User.getCalendar': function (userId) {
			
			let privateEvents = Meteor.call('User.getPrivateAcceptedEventList',userId);
			
			let publicEvents = Meteor.call('User.getPublicAcceptedEventList',userId);

			let events = {'privateEvents':privateEvents , 'publicEvents' : publicEvents }

			return events
		},
		'User.getPrivateAcceptedEventList':function(userId){
			
			let privateEvents = [];

			//Need to filter only active events
			UserData.aggregate([
				{$match:{
					$and:[{'userId':userId},{'private-event.response':1} ]
				}},
				{$project:{
					'private-event': { $filter:{
						input: '$private-event',
						as : 'event',
						cond: {$eq: ['$$event.response',1] }
					}},
					_id:0
				}
                }
			]).forEach(function (obj) {
				obj['private-event'].forEach( function(event) {
					// statements
					//privateEvents.push(event.id)
					let s=Meteor.call('PrivateEvent.getDetails', event.id)
					privateEvents.push (  s)
				});
			});

			return privateEvents		
			
		},
		'User.getPublicAcceptedEventList':function(userId){
			
			let publicEvents = [];

			//Need to filter only active events
			UserData.aggregate([
				{$match:{
					$and:[{'userId':userId},{'public-event.response':1} ]
				}},
				{$project:{
					'public-event': { $filter:{
						input: '$public-event',
						as : 'event',
						cond: {$eq: ['$$event.response',1] }
					}},
					_id:0
				}
                }
			]).forEach(function (obj) {
				obj['public-event'].forEach( function(event) {
					// statements
					//privateEvents.push(event.id)
					let s=Meteor.call('Public.getDetails', event.id)
					publicEvents.push (  s)
				});
			});

			return publicEvents		
			
		},
		'User.addToPrivate':function(userId,eventId,response=0){

			//return console.log(eventId);
			return UserData.update({'userId':userId},{ $push:{'private-event':{'id':eventId,'response':response} }} )
		},
		'User.addToPublic':function(userId,eventId,response = 0){

			//return console.log(eventId);
			if( UserData.update({'userId':userId},{ $push:{'public-event':{'id':eventId,'response':response} }} ) ){
				return "Public Event added "
			}
			else{
				return "Public Event adding failed"
			}
		},
		'User.createAccount': function(user){

			let uid=Accounts.createUser({
						username:user.username,
						password:user.password,
						email:user.password,
						profile:{
							name:user.profilename
						}
					})
			return UserData.insert({'userId':uid})
		},
		'User.getNotificationTab':function(user){
			let list={};

			 let pe = Meteor.call('Private.getNotifTab',user);
			 let pu = Meteor.call('Public.getNotifTab',user);

			 if(pe.length > 0){
			 	list['private-event'] = pe
			 }
			 if(pu.length > 0){
			 	list['public-event'] = pe
			 }

				function isEmpty(obj) {
				    for(var key in obj) {
				        if(obj.hasOwnProperty(key))
				            return false;
				    }
				    return true;
				}

				if(isEmpty(list)){
					list.status = "empty"
				}
				else
					list.status = pe.length + pu.length


					
				
			return list
		},
		/*
		'User.checkPrivate':function(eventId,userId){
			Boolean check = UserData.find({ $and:[ {"userId":userId} , {"private-event" : {$elemMatch : {"id":eventId} } }] }).count() >0
			return check;
		},
		'User.checkPublic':function(eventId,userId){
			Boolean check = UserData.find({$and:[ {"userId":userId} , {"public-event" : {$elemMatch : {"id":eventId} } }] }).count() >0
			return check;
		},
		'User.checkRemainder':function(eventId,userId){
			Boolean check = UserData.find({$and:[ {"userId":userId} , {"remainder" : {$elemMatch : {"id":eventId} } }] }).count() >0
			return check;
		},
		'User.checkPolled':function(eventId,userId){
			Boolean check = UserData.find({$and:[ {"userId":userId} , {"polled-event" : {$elemMatch : {"id":eventId} } }] }).count() >0
			return check;
		},


		*/



























		//Test Cases
		'User.tester': function(){

			return UserData.aggregate([
				{$match:{
					$and:[{'userId':"W9Rq9pQ7nqPSKCpgB"},{'private-event.response':1} ]
				}},
				{$project:{
					'private-event': { $filter:{
						input: '$private-event',
						as : 'event',
						cond: {$eq: ['$$event.response',1] }
					}},
					_id:0
				}
                            }
			])
		},
		'User.testUser': function(){
			let uid=Accounts.createUser({
						username: 'sri',
						password: 'qwe',
						email: 'sri@gmail.com',
						profile: {
							name: 'Srikant'
						}
					})

			//return UserData.insert({'userId':uid})
		},
		'User.test':function(){
			return Accounts.findUserByEmail("abel@gmail.com")
		}
		

	});


}

/*
Accounts.createUser = _.wrap(Accounts.createUser , function(createUser){

		// Store the original arguments
    var args = _.toArray(arguments).slice(1),
        user = args[0];
        origCallback = args[1];

    var newCallback = function(error) {
        // do my stuff
        UserData.insert({'userId':user})

        origCallback.call(this, error);
    };

    createUser(user, newCallback);
	});
	*/


Meteor.users.after.insert(function (userId, doc) {
    UserData.insert({'userId':doc._id})
});
