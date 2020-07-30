import { SimpleSchema } from 'meteor/aldeed:simple-schema';

Schema = {}


Schema.Remainder = new SimpleSchema({
  "name":{
    type: String
  },
  "description":{
    type:String
  },
  "start-date":{
    type:Date
  },
  "end-date":{
    type:Date
  },
  "location":{
    type:Array,
    optional:true
  },
  "location.$":{
    type:Object
  },
  "location.$.name":{
    type:String
  },
  "location.$.longlat":{
    type:String,
    optional:true
  },
  "owner":{
    type:String,
  }
});

Schema.Private = new SimpleSchema ({
  "name":{
    type: String,
    index:true
  },
  "description":{
    type:String
  },
  "start-date":{
    type:Date,
    index:true
  },
  "end-date":{
    type:Date
  },
  "location":{
    type:String,
    index:true
  },
  "owner":{
    type:String
  },
  "invite-list":{
    type:Array,
    optional:true
  },
  "invite-list.$":{
    type:Object
  },
  "invite-list.$.mail":{
    type:String,
    optional:true,
    regEx: SimpleSchema.RegEx.Email
  },
  "invite-list.$.phone":{
    type:Number,
    optional:true
  },
  "invite-list.$.userId":{
    type:String,
    optional:true
  },
  "invite-list.$.response":{
    type:Number,
    allowedValues:[0,1,2],
   
    label:"0-Waiting,1-Accept,2-reject",
    autoValue : function(){
      if(this.isInsert ){
        return 0;
      }

    }
  },
  "guest-list":{
    type:Array,
    optional:true
  },
  "guest-list.$":{
    type:Object
  },
  "guest-list.$.userId":{
    type:String,
    optional:true
  },
  "guest-list.$.mail":{
    type:String,
    optional:true,
    regEx: SimpleSchema.RegEx.Email
  },
  "guest-list.$.phone":{
    type:Number,
    optional:true
  }
});

Schema.UserData = new SimpleSchema({
  "userId":{
    type:String,
    index:true,
    unique:true,
  },
  'remainder':{
    type:Array,
    optional:true
  },
  'remainder.$':{
    type:String
    
  },
  'private-event':{
    type:Array,
    optional:true
  },
  'private-event.$':{
    type:Object,
    optional:true
  },
  'private-event.$.id':{
    type:String,
  },
  'private-event.$.response':{
    type:Number,
    allowedValues:[0,1,2],
    label:"0-Not responded,1-Accept,2-Reject"
    
  },
  'polled':{
    type:Array,
    optional:true
  },
  'polled.$':{
    type:String
  },
  'public-event':{
    type:Array,
    optional:true
  },
  'public-event.$':{
    type:Object,
    optional:true
  },
  'public-event.$.id':{
    type:String
  },
  'public-event.$.response':{
    type:Number,
    allowedValues:[0,1,2],
    label:"0-Not responded,1-Accept,2-Reject"
    
  },
  

})

Schema.Public = new SimpleSchema({
  "name":{
    type: String,
    index:true
  },
  "description":{
    type:String
  },
  "start-date":{
    type:Date,
    index:true
  },
  "end-date":{
    type:Date
  },
  "location":{
    type:String,
    index:true
  },
  "owner":{
    type:String
  },
  "invite-list":{
    type:Array,
    optional:true
  },
  "invite-list.$":{
    type:Object
  },
  "invite-list.$.mail":{
    type:String,
    optional:true,
    regEx: SimpleSchema.RegEx.Email
  },
  "invite-list.$.phone":{
    type:Number,
    optional:true
  },
  "invite-list.$.userId":{
    type:String,
    optional:true
  },
  "invite-list.$.response":{
    type:Number,
    allowedValues:[0,1,2],
    label:"0-Waiting,1-Accept,2-reject",
    autoValue : function(){
      if(this.isInsert){
        return 0
      }
    }
  }
})

export default Schema
