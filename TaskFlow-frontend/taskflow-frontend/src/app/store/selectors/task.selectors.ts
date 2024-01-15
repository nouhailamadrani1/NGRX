import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as fromTask from '../reducers/task.reducer';

export const selectTaskState = createFeatureSelector<fromTask.TaskState>('tasks');

export const selectTasks = createSelector(
    selectTaskState,
    fromTask.selectTasks
);

export const selectLoading = createSelector(
    selectTaskState,
    fromTask.selectLoading
);

export const selectError = createSelector(
    selectTaskState,
    fromTask.selectError
);

export const TaskSelectors = {
    selectTaskState,
    selectTasks,
    selectLoading,
    selectError,
};