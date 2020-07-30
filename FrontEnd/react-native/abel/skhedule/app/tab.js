import React, { Component } from 'react';
import { View, StyleSheet,StatusBar } from 'react-native';
import { TabViewAnimated, TabBar } from 'react-native-tab-view';
import Icon from 'react-native-vector-icons/Ionicons';
import NavigationBar from 'react-native-navbar';
import CalendarTab from './ctab'
import { SearchBar } from 'react-native-elements'
import  ProfileTab  from './profileTab'



const styles = StyleSheet.create({
  container: {
    flex: 1,

  },
  tabbar: {
    backgroundColor: '#296684',
  },
  page: {
    flex:1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  indicator: {
    backgroundColor: '#ffeb3b',
  },
});

export default class TabScreen extends Component {

  static title = 'Icon only top bar';
  static appbarElevation = 0;

  static propTypes = {
    style: View.propTypes.style,
  };

  state = {
    index: 0,
    routes: [
      { key: '1', icon: 'md-calendar' },
      { key: '2', icon: 'md-menu' },
      { key: '3', icon: 'md-home' },
    ],
  };

  _handleChangeTab = (index) => {
    this.setState({
      index,
    });
  };

  _renderIcon = ({ route }: any) => {
    return (
      <Icon
        name={route.icon}
        style={{fontSize: 20, color: 'white'}}

      />
    );
  };

  _renderHeader = (props) => {
    return (
      <TabBar
        {...props}
        indicatorStyle={styles.indicator}
        renderIcon={this._renderIcon}
        style={styles.tabbar}
      />
    );
  };

  _renderScene = ({ route }) => {
    switch (route.key) {
    case '1':
      return (
        <CalendarTab />


            );

    case '2':
      return <View style={[ styles.page, { backgroundColor: '#673ab7' } ]} />;
    case '3':
      return (
        <ProfileTab />
      );

    default:
      return null;
    }
  };

  render() {
    const rightButtonConfig = {
      title: 'Next',

      handler: () => alert('hello!'),
    };

    const titleConfig = {
      title: 'Skhedule',
      tintColor:'#fff',
    };
    const statusBarConfig = {
      style:'light-content',
      hidden:true,
      tintColor:'#296684'

    }
    return (
      <View style={styles.container}>

        <StatusBar
          backgroundColor="#296690"
          barStyle="light-content"
        />

        <NavigationBar tintColor="#296684"
        title={titleConfig}
        rightButton={<Icon name="md-search" style={{fontSize:28, paddingRight:20,paddingTop:10,color:'white'}} onPress={()=>alert('Hello')} />}
        statusBar={statusBarConfig}/>


        <TabViewAnimated
          style={ styles.container }
          navigationState={this.state}
          renderScene={this._renderScene}
          renderHeader={this._renderHeader}
          onRequestChangeTab={this._handleChangeTab}
        />


      </View>

    );
  }
}
