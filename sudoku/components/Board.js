import React from 'react';
import { View, StyleSheet } from 'react-native';
import Cell from './Cell';

const Board = ({ board, onCellPress, selectedCell }) => {

    const getBorderStyle = (rowIndex, colIndex) => {
        let borderStyle = {};

        // Add a thicker right border on the 3rd and 6th cells, except for the last column
        if ((colIndex + 1) % 3 === 0 && colIndex !== 8) {
            borderStyle.borderRightWidth = 3;
            borderStyle.borderRightColor = '#000';
        }

        // Add a thicker bottom border on the 3rd and 6th cells, except for the last row
        if ((rowIndex + 1) % 3 === 0 && rowIndex !== 8) {
            borderStyle.borderBottomWidth = 3;
            borderStyle.borderBottomColor = '#000';
        }

        return borderStyle;
    };

    return (
        <View style={styles.board}>
            {board.map((row, rowIndex) => (
                <View key={`row-${rowIndex}`} style={styles.row}>
                    {row.map((cell, colIndex) => {
                        const isSelected = selectedCell.row === rowIndex && selectedCell.col === colIndex;
                        return (
                            <Cell
                                key={`cell-${rowIndex}-${colIndex}`}
                                number={cell.number}
                                isEditable={cell.isEditable}
                                isMarked={cell.isMarked}
                                isSelected={isSelected}
                                onSelect={() => onCellPress(rowIndex, colIndex)}
                                style={[styles.cell, getBorderStyle(rowIndex, colIndex)]}
                            />
                        );
                    })}
                </View>
            ))}
        </View>
    );
};

const styles = StyleSheet.create({
    board: {
        alignSelf: 'center',
        maxWidth: '90%',
        aspectRatio: 1,
        borderWidth: 3,
        borderColor: '#000000',
    },
    row: {
        flexDirection: "row",
        justifyContent: 'center',
    },
});

export default Board;
