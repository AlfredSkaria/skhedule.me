import { Meteor } from 'meteor/meteor'
//import './privateEvent'
import { Restivus } from 'meteor/nimble:restivus'



	var ApiTest = new Restivus({

		
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
    });

 

	

	ApiTest.addRoute('apitest/private/add',{authRequired:true},{
		get: {
			action: function(){
				//Meteor.call('PrivateEvent.testAddEvent');
				return Meteor.call('PrivateEvent.testCreateEvent',this.userId);
				

			}
		}
	})

	