import { createFeatureSelector, createSelector } from '@ngrx/store';
import * as fromTask from '../reducers/task.reducer';

export const selectTaskState = createFeatureSelector<fromTask.TaskState>('tasks');

export const selectTasks = createSelector(
    selectTaskState,
    state => state.tasks
);

export const selectLoading = createSelector(
    selectTaskState,
    state => state.loading
);

export const selectError = createSelector(
    selectTaskState,
    state => state.error
);
