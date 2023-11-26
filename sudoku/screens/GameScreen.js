import React, { useState } from 'react';
import { View, StyleSheet, Button } from 'react-native';
import Board from '../components/Board';
import NumberSelector from '../components/NumberSelector';

const initialBoard = new Array(9).fill(null).map(() => new Array(9).fill({
    number: null,
    isEditable: true,
    isMarked: false,
}));

const GameScreen = () => {
    const [board, setBoard] = useState(initialBoard);
    const [selectedCell, setSelectedCell] = useState({ row: -1, col: -1 });

    const handleCellSelect = (row, col) => {
        setSelectedCell({ row, col });
    };

    const handleNumberSelect = (number) => {
        if (selectedCell.row !== -1 && selectedCell.col !== -1) {
            const newBoard = board.map((row, rowIndex) => {
                if (rowIndex === selectedCell.row) {
                    return row.map((cell, colIndex) => {
                        if (colIndex === selectedCell.col && cell.isEditable) {
                            return { ...cell, number };
                        }
                        return cell;
                    });
                }
                return row;
            });
            setBoard(newBoard);
        }
    };

    const markCell = () => {
        if (selectedCell.row !== -1 && selectedCell.col !== -1) {
            const newBoard = board.map((row, rowIndex) => {
                return row.map((cell, colIndex) => {
                    if (rowIndex === selectedCell.row && colIndex === selectedCell.col) {
                        return { ...cell, isMarked: !cell.isMarked };
                    }
                    return cell;
                });
            });
            setBoard(newBoard);
        } else {
            Alert.alert('No cell selected', 'Please select a cell to mark it.');
        }
    };

    const clearCell = () => {
        if (selectedCell.row !== -1 && selectedCell.col !== -1) {
            const newBoard = board.map((row, rowIndex) => {
                return row.map((cell, colIndex) => {
                    if (rowIndex === selectedCell.row && colIndex === selectedCell.col && cell.isEditable) {
                        return { ...cell, number: null };
                    }
                    return cell;
                });
            });
            setBoard(newBoard);
        } else {
            Alert.alert('No cell selected', 'Please select a cell to clear it.');
        }
    };

    return (
        <View style={styles.container}>
            <Board
                board={board}
                onCellPress={handleCellSelect}
                selectedCell={selectedCell}
            />
            <NumberSelector onSelectNumber={handleNumberSelect} />
            <View style={styles.buttonContainer}>
                <Button title="Mark" onPress={markCell} />
                <Button title="Clear" onPress={clearCell} />
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    buttonContainer: {
        flexDirection: 'row',
        justifyContent: 'space-around',
        marginTop: 20,
    },
});

export default GameScreen;
