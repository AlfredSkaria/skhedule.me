import {Meteor} from 'meteor/meteor'
import { Mongo } from 'meteor/mongo'
import Schema from './schemas'


const PrivateEvents = new Meteor.Collection('PrivateEvents')

export default PrivateEvents

/*
PrivateEvents.attachSchema(Schema.Private)

Meteor.methods({
  'Private.addEvent':(privateEvent) =>{
    check(privateEvent,{
      'name':String,
      'description':String,
      'start-date':Date,
      'end-date':Date,
      'location':String,
      'invite-list':Array


    })

    try{
      if(!this.userId()) {
        throw new Meteor.Error('500','Must be logged In to create event');
      }
      return PrivateEvents.insert(privateEvent)
    } catch(exception){

      throw new Meteor.Error('500', exception.message);
    }
  },
  'Private.deleteEvent':({eventId})=>{
    check({eventId},
      'eventId':String
    )

    try{
      if(!this.userId()) {
        throw new Meteor.Error('500','Must be logged In to delete event');
      }
      return PrivateEvents.remove({"_id":eventId})
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
      return PrivateEvents.update({"_id":privateEvent.eventId}, {$set : {'name':privateEvent.name,'description':privateEvent.description,'start-date':privateEvent['start-date'],'end-date':privateEvent['end-date'],'location':privateEvent.location }})
    } catch(exception){

      throw new Meteor.Error('500', exception.message);
    }
  },
  'Private.addInvite': ({mail,eventId}) =>{
    check({mail,eventId},{
      'mail':String,
      'eventId':String

    })

    try{
      if(!this.userId()){
        throw new Meteor.Error('500','User Must be logged in to add')

      }

      let oldInvites = PrivateEvents.find({"_id":eventId},{"_id":0,'invite-list':1}).fetch()


      return PrivateEvents.update({"_id":eventId}, {$set : { } } )
    } catch(exception){

      throw new Meteor.Error('500', exception.message);
    }

  }
});

*/
