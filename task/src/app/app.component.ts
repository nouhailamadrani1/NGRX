// src/app/app.component.ts

import { Component, OnInit } from '@angular/core';
import { TaskService } from './services/task.service';
import { Task } from './models/task.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
})
export class AppComponent implements OnInit {
  tasks: Task[] = [];
  newTask: Task = {
    id: 0,
    title: '',
    description: '',
    startDate: '',
    endDate: '',
    status: '',
    // createdBy: null,
    // assignee: null,
    taskTags: [],
  };

  constructor(private taskService: TaskService) {}

  ngOnInit() {
    this.loadTasks();
  }

  loadTasks() {
    this.taskService.getAllTasks().subscribe(tasks => {
      this.tasks = tasks;
    });
  }

  addTask() {
    this.taskService.createTask(this.newTask, 1).subscribe(createdTask => {
      this.tasks.push(createdTask);
      this.newTask = {
        id: 0,
        title: '',
        description: '',
        startDate: '',
        endDate: '',
        status: '',
      
        taskTags: [],
      };
    });
  }

  removeTask(taskId: number) {
    this.taskService.deleteTask(taskId, 1).subscribe(() => {
      this.tasks = this.tasks.filter(task => task.id !== taskId);
    });
  }
}
