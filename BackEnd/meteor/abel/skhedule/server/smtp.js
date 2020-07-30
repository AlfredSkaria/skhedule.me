import { Meteor } from 'meteor/meteor';


Meteor.startup(function () {
process.env.MAIL_URL = 'smtp://apikey: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@smtp.sendgrid.net:587';
});