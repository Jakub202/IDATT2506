import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';

const Cell = ({ number, isEditable, isMarked, isSelected, onSelect, style }) => {
    const cellStyle = [
        styles.cell,
        isEditable ? styles.editableCell : styles.nonEditableCell,
        isMarked && styles.markedCell,
        isSelected && styles.selectedCell,
        style, // Apply additional styles passed as a prop
    ];
    const textStyle = [
        styles.cellText,
        !isEditable && styles.boldText, // Apply bold style if not editable
    ];

    return (
        <TouchableOpacity style={cellStyle} onPress={onSelect}>
            <Text style={textStyle}>{number}</Text>
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
    nonEditableCell: {
        backgroundColor: '#e9e9e9', // You can choose a different color to indicate non-editable cells
    },
    boldText: {
        fontWeight: 'bold',
        color: 'red'// Bold font weight for non-editable numbers
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
