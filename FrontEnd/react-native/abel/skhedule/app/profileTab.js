import React, { Component } from 'react';
import { View, StyleSheet,Text,ScrollView,LayoutAnimation } from 'react-native';
import { Card, ListItem, Button, Grid,Row, Col,SocialIcon,Icon } from 'react-native-elements'
var GiftedListView = require('react-native-gifted-listview');
import ActionButton from 'react-native-action-button';
//import Icon from 'react-native-vector-icons/Ionicons';

const styles = StyleSheet.create({
  page: {
    flex:1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  actionButtonIcon: {
    fontSize: 20,
    height: 22,
    color: 'white',
  },
});

export default class ProfileTab extends Component {

  render(){


    return (

      <View style={styles.page} >
          <ScrollView >

            <Grid>
              <Row size={15}>
                <Icon
                  raised
                  name='heartbeat'
                  type='font-awesome'
                  color='#f50'
                  onPress={() => console.log('hello')} />
              </Row>

              <Row size={85} >


              </Row>
            </Grid>

          </ScrollView>

      </View>
    )
  }
}
