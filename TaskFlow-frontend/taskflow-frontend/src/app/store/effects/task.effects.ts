import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { catchError, map, mergeMap } from 'rxjs/operators';
import * as TaskActions from '../actions/task.actions';
import { TaskService } from 'src/app/services/taskService/task.service';

@Injectable()
export class TaskEffects {
    constructor(private actions$: Actions, private taskService: TaskService) {}

    loadTasks$ = createEffect(() => this.actions$.pipe(
        ofType(TaskActions.loadTasks),
        mergeMap(() => this.taskService.getAllTasks()
        .pipe(
            map(tasks => TaskActions.loadTasksSuccess({ tasks })),
            catchError(error => of(TaskActions.loadTasksFailure({ error })))
        )
        )
    ));
}
