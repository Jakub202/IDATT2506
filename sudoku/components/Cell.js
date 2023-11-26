import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';

const Cell = ({ number, isEditable, isMarked, isSelected, onSelect, style }) => {
    const cellStyle = [
        styles.cell,
        isEditable && styles.editableCell,
        isMarked && styles.markedCell,
        isSelected && styles.selectedCell,
        style, // Apply additional styles passed as a prop
    ];

    return (
        <TouchableOpacity style={cellStyle} onPress={onSelect}>
            <Text style={styles.cellText}>{number}</Text>
        </TouchableOpacity>
    );
};

const styles = StyleSheet.create({
    cell: {
        width: 40,
        height: 40,
        justifyContent: 'center',
        alignItems: 'center',
        borderWidth: 1,
        borderColor: '#000',
    },
    editableCell: {
        backgroundColor: '#fff',
    },
    markedCell: {
        backgroundColor: 'lightblue',
    },
    selectedCell: {
        backgroundColor: 'yellow',
    },
    cellText: {
        fontSize: 16,
    },
});

export default Cell;
