// task.component.ts

import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as TaskActions from 'src/app/store/actions/task.actions';
import * as TaskSelectors from 'src/app/store/selectors/task.selectors';
import { Task } from 'src/app/models/task';
import { Router } from '@angular/router';
import { TaskService } from 'src/app/services/taskService/task.service';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css'],
})
export class TaskComponent implements OnInit {
  tasks$: Observable<Task[]> | undefined;
  loading$: Observable<boolean> | undefined;
  error$: Observable<any> | undefined;
  deletedTaskId$: Observable<number | null> | undefined;

  newTask: Task = {
    id: 0,
    title: '',
    description: '',
    priority: '',
    startDate: '',
    dueDate: '',
    createdBy: 1,
    assignedTo:1,
    status: 'IN_PROGRESS', 
    tagIds: [1,2]
  
  };

  constructor(
    private store: Store,
    private router: Router,
    private taskService: TaskService 
  ) {
    this.tasks$ = this.store.select(TaskSelectors.selectTasks);
    this.loading$ = this.store.select(TaskSelectors.selectLoading);
    this.error$ = this.store.select(TaskSelectors.selectError);
  }

  ngOnInit(): void {
    this.store.dispatch(TaskActions.loadTasks());
  }

  hasTasksInProgress(): Observable<boolean> {
    return this.tasks$?.pipe(
      map((tasks) => tasks && tasks.some((task) => task.status === 'IN_PROGRESS'))
    ) || new Observable<boolean>();
  }

  hasUncompletedTasks(): Observable<boolean> {
    return this.tasks$?.pipe(
      map((tasks) => tasks && tasks.some((task) => task.status === 'UNCOMPLETED'))
    ) || new Observable<boolean>();
  }

  hasCompletedTasks(): Observable<boolean> {
    return this.tasks$?.pipe(
      map((tasks) => tasks && tasks.some((task) => task.status === 'COMPLETED'))
    ) || new Observable<boolean>();
  }

  updateTask(task: Task): void {
    this.taskService.updateTask(task).subscribe(
      (updatedTask) => {
        this.store.dispatch(TaskActions.updateTaskSuccess({ task: updatedTask }));
      },
      (error) => {
        this.store.dispatch(TaskActions.updateTaskFailure({ error }));
      }
    );
  }

  deleteTask(task: Task): void {
    if (task) {
      this.store.dispatch(TaskActions.deleteTask({ taskId: task.id }));
    }
  }

  createTask(): void {
    this.taskService.createTask(this.newTask).subscribe(
      (newTask) => {
        this.store.dispatch(TaskActions.addTaskSuccess({ task: newTask }));
      
        this.newTask = {
          id: 0,
          title: '',
          description: '',
          priority: '',
          startDate: '',
          dueDate: '',
          createdBy:1,
          assignedTo:1,
          status: 'IN_PROGRESS', 
          tagIds: [1,2]
          
        };
      },
      (error) => {
        this.store.dispatch(TaskActions.addTaskFailure({ error }));
      }
    );
  }
}
