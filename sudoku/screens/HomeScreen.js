import React from 'react';
import { View, Text, Button, StyleSheet } from 'react-native'
import { useTranslation } from "react-i18next";

const HomeScreen = ({ navigation }) => {
    const { t } = useTranslation();
    return (
        <View style={styles.container}>
            <Text style={styles.title}>{t('home.title')}</Text>
            <Button
                title={t('home.go_to_game')}
                onPress={() => navigation.navigate('Game')}
            />
            <Button
                title={t('home.go_to_settings')}
                onPress={() => navigation.navigate('Settings')}
            />
            <Button
                title={t('home.go_to_new_board')}
                onPress={() => navigation.navigate('NewBoard')}
            />
            <Button
                title={t('home.go_to_rules')}
                onPress={() => navigation.navigate('Rules')}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
    title: {
        fontSize: 20,
        fontWeight: 'bold',
    },
});

export default HomeScreen;
