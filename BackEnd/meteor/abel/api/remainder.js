import { Mongo } from 'meteor/mongo';
import { Meteor } from 'meteor/meteor'; // ADD THIS
import {Schema} from './schemas';

Remainder = new Meteor.Collection('Remainder');

Remainder.attachSchema(Schema.Remainder);

Meteor.methods({
  'Remainder.addRemainder' : () => {

  }
});
