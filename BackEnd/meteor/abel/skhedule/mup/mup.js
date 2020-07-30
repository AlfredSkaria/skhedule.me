module.exports = {
  servers: {
    one: {
      host: '139.59.42.83',
      username: 'karela',
      pem: '~/.ssh/id_rsa',
      // password:
      // or leave blank for authenticate from ssh-agent
    }
  },

  meteor: {
    name: 'skhedule',
    path: '../',
    servers: {
      one: {},
    },
    buildOptions: {
      //serverOnly: true,
      debug:true
    },
    env: {
      ROOT_URL: 'http://skhedule.tk',
      PORT: 3000
    },

    // change to 'kadirahq/meteord' if your app is not using Meteor 1.4
    dockerImage: 'abernix/meteord:base',
    deployCheckWaitTime: 60,
    
    // Show progress bar while uploading bundle to server
    // You might need to disable it on CI servers
    enableUploadProgressBar: false
  },

  mongo: {
    oplog: true,
    port: 27090,
    version: '3.4.1',
    servers: {
      one: {},
    },
  },
};
