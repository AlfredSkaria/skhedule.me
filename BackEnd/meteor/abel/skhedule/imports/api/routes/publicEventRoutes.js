import { Meteor } from 'meteor/meteor'

import { Restivus } from 'meteor/nimble:restivus'

var Api = new Restivus({
	defaultHeaders: {
        'Content-Type': 'application/json'
    },
    prettyJson: true,
    
    enableCors: true,
    useDefaultAuth:true
})

Api.addRoute('event/public/add',{authRequired:true},{
	post:{
		action:function(){
			return Meteor.call('Public.createEvent',this.bodyParams,this.userId)
		}
	}
})

Api.addRoute('event/public/list',{},{
	get:{
		action:function(){
			return Meteor.call('Public.listEvents')
		}
	}
})








Api.addRoute('event/public/testCreateEvent',{authRequired:true},{
	post:{
		action:function(){

			return Meteor.call('Public.testCreateEvent',this.userId)
		}
	}
})