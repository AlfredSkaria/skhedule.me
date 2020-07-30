/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,

} from 'react-native';

import Start from './app/index'
import TabScreen from './app/tab'

export default class skhedule extends Component {


    render() {

        return (
          <TabScreen/>
        );
    }



}


AppRegistry.registerComponent('skhedule', () => skhedule);
