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
    on(TaskActions.loadTasksFailure, (state, { error }) => ({ ...state, error, loading: false }))
);

export const selectTasks = (state: TaskState) => state.tasks;
export const selectLoading = (state: TaskState) => state.loading;
export const selectError = (state: TaskState) => state.error;
