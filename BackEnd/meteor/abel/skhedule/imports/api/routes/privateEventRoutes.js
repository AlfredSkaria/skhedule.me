import { Meteor } from 'meteor/meteor'

import { Restivus } from 'meteor/nimble:restivus'



	var Api = new Restivus({

		
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

    

	

	Api.addRoute('event/private/add',{authRequired:true},{
		post: {
			action: function(){
				
				return Meteor.call('Private.createEvent',this.bodyParams,this.userId);
				

			}
		}
	})

	