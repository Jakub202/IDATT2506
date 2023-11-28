import React from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';
import { useTranslation } from "react-i18next";

const HomeScreen = ({ navigation }) => {
    const { t } = useTranslation();
    return (
        <View style={styles.container}>
            <Text style={styles.title}>{t('home.title')}</Text>
            <TouchableOpacity
                style={styles.button}
                onPress={() => navigation.navigate('Game')}
            >
                <Text style={styles.buttonText}>{t('home.go_to_game')}</Text>
            </TouchableOpacity>
            <TouchableOpacity
                style={styles.button}
                onPress={() => navigation.navigate('Settings')}
            >
                <Text style={styles.buttonText}>{t('home.go_to_settings')}</Text>
            </TouchableOpacity>
            <TouchableOpacity
                style={styles.button}
                onPress={() => navigation.navigate('NewBoard')}
            >
                <Text style={styles.buttonText}>{t('home.go_to_new_board')}</Text>
            </TouchableOpacity>
            <TouchableOpacity
                style={styles.button}
                onPress={() => navigation.navigate('Rules')}
            >
                <Text style={styles.buttonText}>{t('home.go_to_rules')}</Text>
            </TouchableOpacity>
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
        marginBottom: 20, // adds some space below the title
    },
    button: {
        backgroundColor: '#007bff',
        padding: 10,
        marginVertical: 5, // adds padding between buttons
        width: '80%', // sets a fixed width for all buttons
        alignItems: 'center',
        borderRadius: 5,
    },
    buttonText: {
        color: '#ffffff', // sets the text color to white
        fontSize: 16,
        textAlign: 'center',
    },
});

export default HomeScreen;
