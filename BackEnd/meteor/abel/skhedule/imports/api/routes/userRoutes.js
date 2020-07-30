import { Meteor } from 'meteor/meteor'
import { Restivus } from 'meteor/nimble:restivus'


var UserApi =  new Restivus({

		
	    onLoggedIn: function () {
	      console.log(this.user.username + ' (' + this.userId + ') logged in');
	    },
	    onLoggedOut: function () {
	      console.log(this.user.username + ' (' + this.userId + ') logged out');
	    },

        
        defaultHeaders: {
            'Content-Type': 'application/json'
        },
        prettyJson: true,
        
        enableCors: true,
        useDefaultAuth:true
    })

UserApi.addCollection(Meteor.users , {
	excludedEndpoints : ['getAll','put','get','delete']
})

UserApi.addRoute('calendar/all',{authRequired:true},{
		get: {
			action: function(){
				let res=Meteor.call('User.getCalendar',this.userId)
				return res;
			}
		}
})

UserApi.addRoute('users/signup',{},{
		post:{
			action: function(){
				let user = {'profilename':this.body.params.name,
							'username': this.body.params.uname,
							'password':this.body.params.password,
							'email':this.body.params.email};
				 return Meteor.call('User.createAccount', user );
			}

			
		}
})

UserApi.addRoute('user/:id',{authRequired:true},{
	get : {
		action: function(){
			return Meteor.call('User.getDetails',this.urlParams.id);
		}
	}
})

UserApi.addRoute('notification/all',{authRequired:true},{
	get : {
		action: function(){
			return Meteor.call('User.getNotificationTab',this.userId);
		}
	}
})
