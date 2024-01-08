// src/app/store/task.reducer.ts

import { createReducer, on } from '@ngrx/store';
import * as taskActions from './task.actions';
import { Task } from '../models/task.model';

export interface TaskState {
  tasks: Task[];
}

export const initialState: TaskState = {
  tasks: [],
};

export const taskReducer = createReducer(
  initialState,
  on(taskActions.addTask, (state, { task }) => ({ ...state, tasks: [...state.tasks, task] })),
  on(taskActions.removeTask, (state, { taskId }) => ({ ...state, tasks: state.tasks.filter(task => task.id !== taskId) }))
);
