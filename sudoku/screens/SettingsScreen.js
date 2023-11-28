import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import i18next from '../i18n/i18n.js';
import { useTranslation } from "react-i18next";

const SettingsScreen = () => {
    const { t } = useTranslation();

    const handleChangeLanguage = (language) => {
        console.log(`Changing language to: ${language}`);
        i18next.changeLanguage(language).then(() => {
            console.log(`Language changed to: ${i18next.language}`);
        }).catch(error => {
            console.error(`Failed to change language: ${error.message}`);
        });
    };

    return (
        <View style={styles.container}>
            <Text style={styles.title}>{t('greeting')}</Text>

            <TouchableOpacity style={styles.button} onPress={() => handleChangeLanguage('en')}>
                <Text style={styles.buttonText}>English</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.button} onPress={() => handleChangeLanguage('no')}>
                <Text style={styles.buttonText}>Norsk</Text>
            </TouchableOpacity>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
        padding: 10,
    },
    title: {
        fontSize: 20,
        fontWeight: 'bold',
        marginBottom: 20,
    },
    button: {
        backgroundColor: '#007bff',
        paddingVertical: 10,
        paddingHorizontal: 20,
        borderRadius: 5,
        marginVertical: 10,
    },
    buttonText: {
        color: '#ffffff',
        fontSize: 16,
        textAlign: 'center',
    },
    // Additional styles as needed
});

export default SettingsScreen;
