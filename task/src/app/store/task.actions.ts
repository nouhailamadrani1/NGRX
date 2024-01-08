
import { createAction, props } from '@ngrx/store';
import { Task } from '../models/task.model';

export const addTask = createAction('[Task] Add Task', props<{ task: Task }>());
export const removeTask = createAction('[Task] Remove Task', props<{ taskId: number }>());
