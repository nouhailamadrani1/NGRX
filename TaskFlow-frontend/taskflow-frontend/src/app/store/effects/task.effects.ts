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

    addTask$ = createEffect(() => this.actions$.pipe(
        ofType(TaskActions.addTask),
        mergeMap(({ task }) => this.taskService.createTask(task)
            .pipe(
                map(newTask => TaskActions.addTaskSuccess({ task: newTask })),
                catchError(error => of(TaskActions.addTaskFailure({ error })))
            )
        )
    ));

    updateTask$ = createEffect(() => this.actions$.pipe(
        ofType(TaskActions.updateTask),
        mergeMap(({ task }) => this.taskService.updateTask(task)
            .pipe(
                map(updatedTask => TaskActions.updateTaskSuccess({ task: updatedTask })),
                catchError(error => of(TaskActions.updateTaskFailure({ error })))
            )
        )
    ));

    deleteTask$ = createEffect(() => this.actions$.pipe(
        ofType(TaskActions.deleteTask),
        mergeMap(({ taskId }) => this.taskService.deleteTask(taskId)
            .pipe(
                map(() => TaskActions.deleteTaskSuccess({ taskId })),
                catchError(error => of(TaskActions.deleteTaskFailure({ error })))
            )
        )
    ));
}
