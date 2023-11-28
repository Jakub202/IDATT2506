import React from 'react';
import { View, Text, ScrollView, StyleSheet } from 'react-native';
import { useTranslation } from 'react-i18next';

const RulesScreen = () => {
    const { t } = useTranslation();

    return (
        <ScrollView style={styles.container}>
            <Text style={styles.title}>{t('rules.title')}</Text>
            <Text style={styles.rule}>{t('rules.rule1')}</Text>
            <Text style={styles.rule}>{t('rules.rule2')}</Text>
            <Text style={styles.rule}>{t('rules.rule3')}</Text>
            <Text style={styles.rule}>{t('rules.rule4')}</Text>
            <Text style={styles.rule}>{t('rules.rule5')}</Text>
        </ScrollView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 10,
    },
    title: {
        fontSize: 22,
        fontWeight: 'bold',
        textAlign: 'center',
        marginBottom: 20,
    },
    rule: {
        fontSize: 16,
        marginBottom: 10,
    },
    // Additional styles as needed
});

export default RulesScreen;
