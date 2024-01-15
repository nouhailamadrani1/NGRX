import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as TaskActions from 'src/app/store/actions/task.actions';
import * as TaskSelectors from 'src/app/store/selectors/task.selectors';
import { Task } from 'src/app/models/task';
import { Router } from '@angular/router';


@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css'],
})
export class TaskComponent implements OnInit {
  tasks$: Observable<Task[]> | undefined;
  loading$: Observable<boolean> | undefined;
  error$: Observable<any> | undefined;

  constructor(private store: Store ,private router: Router) {
    this.tasks$ = this.store.select(TaskSelectors.selectTasks);
    this.loading$ = this.store.select(TaskSelectors.selectLoading);
    this.error$ = this.store.select(TaskSelectors.selectError);
  }

  ngOnInit(): void {
    this.store.dispatch(TaskActions.loadTasks());
  }

  hasTasksInProgress(): Observable<boolean> {
    return this.tasks$?.pipe(
      map(tasks => tasks && tasks.some(task => task.status === 'IN_PROGRESS'))
    ) || new Observable<boolean>();
  }

  hasUncompletedTasks(): Observable<boolean> {
    return this.tasks$?.pipe(
      map(tasks => tasks && tasks.some(task => task.status === 'UNCOMPLETED'))
    ) || new Observable<boolean>();
  }

  hasCompletedTasks(): Observable<boolean> {
    return this.tasks$?.pipe(
      map(tasks => tasks && tasks.some(task => task.status === 'COMPLETED'))
    ) || new Observable<boolean>();
  }

  updateTask(task: Task): void {
    // Implement your update logic here
    console.log('Updating task:', task);
  }

  deleteTask(task: Task): void {
    // Implement your delete logic here
    console.log('Deleting task:', task);
  }

  createNewTask(): void {
    // Navigate to the 'new-task' route
    this.router.navigate(['new-task']);
  }
}
