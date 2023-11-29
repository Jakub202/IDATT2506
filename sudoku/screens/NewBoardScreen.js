import React, { useState } from 'react';
import {View, StyleSheet, Button, Alert, Text, Pressable} from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import Board from '../components/Board';
import NumberSelector from "../components/NumberSelector"; // Reuse the same Board component
import { useTranslation } from 'react-i18next';

const initialBoardState = new Array(9).fill(null).map(() => new Array(9).fill({
    number: null,
    isEditable: true,
}));

const NewBoardScreen = () => {
    const [board, setBoard] = useState(initialBoardState);
    const [selectedCell, setSelectedCell] = useState({ row: -1, col: -1 });
    const { t } = useTranslation();

    const handleCellSelect = (row, col) => {
        setSelectedCell({ row, col });
    };

    const handleNumberSelect = (number) => {
        if (selectedCell.row !== -1 && selectedCell.col !== -1) {
            const newBoard = [...board];
            newBoard[selectedCell.row][selectedCell.col] = {
                ...newBoard[selectedCell.row][selectedCell.col],
                number: number
            };
            setBoard(newBoard);
        }
    };

    const handleSaveBoard = async (difficulty) => {
        // Transform the board data to match the required format
        const boardToSave = board.map(row =>
            row.map(cell => cell.number) // We just need the number for each cell
        );

        const boardObject = {
            board: boardToSave,
            difficulty: difficulty
        };

        try {
            const savedBoards = await AsyncStorage.getItem('savedBoards') || '[]';
            const boards = JSON.parse(savedBoards);

            // Add the new board to the array of saved boards
            boards.push(boardObject);

            // Save the updated array of boards to AsyncStorage
            await AsyncStorage.setItem('savedBoards', JSON.stringify(boards));

            // Log the saved board for debugging purposes
            console.log("Board saved:", boardObject);

            Alert.alert(t('alerts.board_saved'));
        } catch (error) {
            console.error("Error saving board:", error);
            Alert.alert(t('alerts.board_not_saved'));
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
            Alert.alert(t('alerts.no_cell_selected'));
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
            Alert.alert(t('alerts.no_cell_selected'));
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
                <Pressable style={styles.button} onPress={markCell}>
                    <Text style={styles.buttonText}>{t('new_board.mark')}</Text>
                </Pressable>
                <Pressable style={styles.button} onPress={clearCell}>
                    <Text style={styles.buttonText}>{t('new_board.clear')}</Text>
                </Pressable>
            </View>
            <Text>{t('new_board.save_board_as')}</Text>
            <View style={styles.buttonContainer}>
                <Pressable style={styles.button} onPress={() => handleSaveBoard('easy')}>
                    <Text style={styles.buttonText}>{t('new_board.easy')}</Text>
                </Pressable>
                <Pressable style={styles.button} onPress={() => handleSaveBoard('medium')}>
                    <Text style={styles.buttonText}>{t('new_board.medium')}</Text>
                </Pressable>
                <Pressable style={styles.button} onPress={() => handleSaveBoard('hard')}>
                    <Text style={styles.buttonText}>{t('new_board.hard')}</Text>
                </Pressable>
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'space-around',
        alignItems: 'center',
        padding: 10,
    },
    saveAsText: {
        fontSize: 24,
        fontWeight: 'bold',
        marginVertical: 20,
    },
    buttonContainer: {
        flexDirection: 'row',
        justifyContent: 'space-around',
        width: '100%',
        paddingHorizontal: 20,
    },
    button: {
        backgroundColor: '#007bff',
        paddingVertical: 10,
        paddingHorizontal: 20,
        borderRadius: 5,
        marginHorizontal: 5,
    },
    buttonText: {
        color: '#ffffff',
        fontSize: 18,
        textAlign: 'center',
    },
});



export default NewBoardScreen;
