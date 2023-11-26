import React from 'react';
import { View, TouchableOpacity, Text, StyleSheet } from 'react-native';

const NumberSelector = ({ onSelectNumber }) => {
    const numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9]; // Sudoku numbers

    return (
        <View style={styles.container}>
            {numbers.map((number) => (
                <TouchableOpacity
                    key={number}
                    style={styles.number}
                    onPress={() => onSelectNumber(number)}
                >
                    <Text style={styles.numberText}>{number}</Text>
                </TouchableOpacity>
            ))}
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flexDirection: 'row',
        justifyContent: 'center',
        padding: 10,
    },
    number: {
        padding: 10,
        margin: 5,
        borderWidth: 1,
        borderColor: '#ddd',
        borderRadius: 5,
    },
    numberText: {
        fontSize: 16,
    },
});

export default NumberSelector;
