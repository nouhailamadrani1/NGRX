// task-form.component.ts

import { Component } from '@angular/core';
import { TaskService } from 'src/app/services/taskService/task.service'; 
import { Task } from 'src/app/models/task';

@Component({
  selector: 'app-task-form',
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.css']
})
export class TaskFormComponent {
  newTask: Task = {
    id: 0,
    title: '',
    description: '',
    priority: 'HIGH',
    startDate: '',
    dueDate: '',
    createdBy: null!,
    assignedTo: null!,
    status: 'IN_PROGRESS',
    tags: [],
    tokenDemands: []
  };

  constructor(private taskService: TaskService) {}

  createNewTask(form: any): void {
    if (form.valid) {
      this.taskService.createTask(this.newTask).subscribe(
        response => {
          console.log('Task created successfully:', response);
          form.resetForm();
          this.newTask = {
            id: 0,
            title: '',
            description: '',
            priority: 'HIGH',
            startDate: '',
            dueDate: '',
            createdBy: null!,
            assignedTo: null!,
            status: 'IN_PROGRESS',
            tags: [],
            tokenDemands: []
          };
        },
        error => {
          console.error('Error creating task:', error);
        }
      );
    } else {
      console.log('Form is invalid. Please check the fields.');
    }
  }
}
