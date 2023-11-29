import React, {useEffect, useState} from 'react';
import { View, StyleSheet, Button, Text, Pressable, Alert } from 'react-native';
import Board from '../components/Board';
import NumberSelector from '../components/NumberSelector';
import AsyncStorage from '@react-native-async-storage/async-storage';
import easyBoard from '../assets/data/easy.json';
import mediumBoard from '../assets/data/medium.json';
import hardBoard from '../assets/data/hard.json';
import { useTranslation } from 'react-i18next';


const initialBoard = new Array(9).fill(null).map(() => new Array(9).fill({
    number: null,
    isEditable: true,
    isMarked: false,
}));

const GameScreen = () => {
    const [board, setBoard] = useState(initialBoard);
    const [selectedCell, setSelectedCell] = useState({ row: -1, col: -1 });
    const { t } = useTranslation();

    useEffect(() => {
        loadSavedBoard();
    }, []);

    const loadSavedBoard = async () => {
        try {
            const savedBoard = await AsyncStorage.getItem('currentBoard');
            if (savedBoard) {
                console.log('Saved board retrieved')
                setBoard(JSON.parse(savedBoard));
            }
        } catch (error) {
            Alert.alert('Error', 'Failed to load the saved board.');
        }
    };



    const transformBoardData = (data) => {
        return data.map(row => row.map(cellValue => ({
            number: cellValue,
            isEditable: cellValue === null,
            isMarked: false,
        })));
    };

    const loadBoard = async (difficulty) => {
        try {
            const savedBoards = await AsyncStorage.getItem('savedBoards');
            let boards = savedBoards ? JSON.parse(savedBoards) : [];
            console.log("Retrieved boards:", boards);

            let boardsByDifficulty = boards.filter(board => board.difficulty === difficulty);

            let newBoardData;
            if (boardsByDifficulty.length > 0) {
                // Pick a random board from the filtered list
                let randomIndex = Math.floor(Math.random() * boardsByDifficulty.length);
                newBoardData = transformBoardData(boardsByDifficulty[randomIndex].board);
            } else {
                // Load default board based on difficulty
                switch (difficulty) {
                    case 'easy':
                        newBoardData = transformBoardData(easyBoard.board);
                        break;
                    case 'medium':
                        newBoardData = transformBoardData(mediumBoard.board);
                        break;
                    case 'hard':
                        newBoardData = transformBoardData(hardBoard.board);
                        break;
                    default:
                        newBoardData = initialBoard; // Default to an empty board
                }
            }
            console.log('Loaded board:', newBoardData);
            setBoard(newBoardData);
            saveBoard(newBoardData);
        } catch (error) {
            console.error('Failed to load the board:', error);
        }
    };

    const saveBoard = async (currentBoard) => {
        try {
            const stringifiedBoard = JSON.stringify(currentBoard);
            console.log('Saving board');
            await AsyncStorage.setItem('currentBoard', stringifiedBoard);
        } catch (error) {
            Alert.alert('Error', 'Failed to save the board.');
        }
    };

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
            saveBoard(newBoard);
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
            saveBoard(newBoard);
        } else {
            Alert.alert('No cell selected', 'Please select a cell to clear it.');
        }
    };

    const checkBoardValidity = () => {
        // Check for empty cells first
        for (const row of board) {
            for (const cell of row) {
                if (cell.number === null) {
                    Alert.alert(t('alerts.incomplete_board'));
                    return false;
                }
            }
        }

        // Check rows for validity
        for (let row = 0; row < 9; row++) {
            if (!checkSectionValidity(board[row].map(cell => cell.number))) {
                Alert.alert(t('alerts.invalid_board'));
                return false;
            }
        }

        // Check columns for validity
        for (let col = 0; col < 9; col++) {
            const column = board.map(row => row[col].number);
            if (!checkSectionValidity(column)) {
                Alert.alert(t('alerts.invalid_board'));
                return false;
            }
        }

        // Check 3x3 blocks for validity
        for (let row = 0; row < 9; row += 3) {
            for (let col = 0; col < 9; col += 3) {
                const block = [];
                for (let r = 0; r < 3; r++) {
                    for (let c = 0; c < 3; c++) {
                        block.push(board[row + r][col + c].number);
                    }
                }
                if (!checkSectionValidity(block)) {
                    Alert.alert(t('alerts.invalid_board'));
                    return false;
                }
            }
        }

        // If everything is valid
        Alert.alert(t('alerts.valid_board'));
        return true;
    };

    const checkSectionValidity = (section) => {
        const seen = new Set();
        for (let i = 0; i < section.length; i++) {
            const value = section[i];
            if (value) {
                if (seen.has(value) || value < 1 || value > 9) {
                    return false; // Duplicate or invalid number
                }
                seen.add(value);
            }
        }
        return true; // No duplicates and all numbers are valid
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
                <Button title={t('game.mark')} onPress={markCell} />
                <Button title={t('game.clear')} onPress={clearCell} />
            </View>
            <Text style={styles.loadBoardTitle}>{t('game.load_new_board')}</Text>
            <View style={styles.buttonContainer}>
                <Pressable style={styles.button} onPress={() => loadBoard('easy')}>
                    <Text style={styles.buttonText}>{t('game.easy')}</Text>
                </Pressable>
                <Pressable style={styles.button} onPress={() => loadBoard('medium')}>
                    <Text style={styles.buttonText}>{t('game.medium')}</Text>
                </Pressable>
                <Pressable style={styles.button} onPress={() => loadBoard('hard')}>
                    <Text style={styles.buttonText}>{t('game.hard')}</Text>
                </Pressable>
            </View>
            <Pressable style={styles.button} onPress={checkBoardValidity}>
                <Text style={styles.buttonText}>{t('game.check_validity')}</Text>
            </Pressable>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'space-around',
        alignItems: 'center',
        paddingTop: 20,
    },
    loadBoardTitle: {
        fontSize: 24,
        fontWeight: 'bold',
        marginTop: 20,
        marginBottom: 20,
    },
    buttonContainer: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        width: '100%',
        paddingHorizontal: 20,
        marginBottom: 20,
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
    numberSelectorContainer: {
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'center',
        marginBottom: 20,
    },

});
export default GameScreen;
