import { createReducer, on } from '@ngrx/store';
import * as TaskActions from '../actions/task.actions';
import { Task } from 'src/app/models/task';

export interface TaskState {
    tasks: Task[];
    loading: boolean;
    error: any;
}

export const initialState: TaskState = {
    tasks: [],
    loading: false,
    error: null,
};

export const taskReducer = createReducer(
    initialState,
    on(TaskActions.loadTasks, state => ({ ...state, loading: true, error: null })),
    on(TaskActions.loadTasksSuccess, (state, { tasks }) => ({ ...state, tasks, loading: false })),
    on(TaskActions.loadTasksFailure, (state, { error }) => ({ ...state, error, loading: false })),

    on(TaskActions.addTask, state => ({ ...state, loading: true, error: null })),
    on(TaskActions.addTaskSuccess, (state, { task }) => ({ ...state, tasks: [...state.tasks, task], loading: false })),
    on(TaskActions.addTaskFailure, (state, { error }) => ({ ...state, error, loading: false })),

    on(TaskActions.updateTask, state => ({ ...state, loading: true, error: null })),
    on(TaskActions.updateTaskSuccess, (state, { task }) => ({
        ...state,
        tasks: state.tasks.map(t => (t.id === task.id ? task : t)),
        loading: false
    })),
    on(TaskActions.updateTaskFailure, (state, { error }) => ({ ...state, error, loading: false })),

    on(TaskActions.deleteTask, state => ({ ...state, loading: true, error: null })),
    on(TaskActions.deleteTaskSuccess, (state, { taskId }) => ({
        ...state,
        tasks: state.tasks.filter(task => task.id !== taskId),
        loading: false
    })),
    on(TaskActions.deleteTaskFailure, (state, { error }) => ({ ...state, error, loading: false }))
);
