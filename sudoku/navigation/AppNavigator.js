import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import HomeScreen from '../screens/HomeScreen';
import GameScreen from '../screens/GameScreen';
import SettingsScreen from '../screens/SettingsScreen';
import NewBoardScreen from "../screens/NewBoardScreen";
import RulesScreen from "../screens/RulesScreen";
import { NavigationContainer } from '@react-navigation/native';
import { useTranslation } from 'react-i18next';

const Stack = createNativeStackNavigator();

const AppNavigator = () => {
    const { t } = useTranslation();

    return (
        <NavigationContainer>
            <Stack.Navigator initialRouteName="Home">
                <Stack.Screen
                    name="Home"
                    component={HomeScreen}
                    options={{ title: t('navigation.home') }}
                />
                <Stack.Screen
                    name="Game"
                    component={GameScreen}
                    options={{ title: t('navigation.game') }}
                />
                <Stack.Screen
                    name="Settings"
                    component={SettingsScreen}
                    options={{ title: t('navigation.settings') }}
                />
                <Stack.Screen
                    name="NewBoard"
                    component={NewBoardScreen}
                    options={{ title: t('navigation.newBoard') }}
                />
                <Stack.Screen
                    name="Rules"
                    component={RulesScreen}
                    options={{ title: t('navigation.rules') }}
                />
            </Stack.Navigator>
        </NavigationContainer>
    );
};

export default AppNavigator;
