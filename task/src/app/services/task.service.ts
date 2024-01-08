// src/app/services/task.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task } from '../models/task.model';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private baseUrl = 'http://localhost:8080/taskflow/tasks';

  constructor(private http: HttpClient) {}

  getAllTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.baseUrl}`);
  }

  getTaskById(taskId: number): Observable<Task> {
    return this.http.get<Task>(`${this.baseUrl}/${taskId}`);
  }

  createTask(task: Task, currentUserId: number): Observable<Task> {
    return this.http.post<Task>(`${this.baseUrl}/${currentUserId}`, task);
  }

  updateTask(taskId: number, task: Task): Observable<Task> {
    return this.http.put<Task>(`${this.baseUrl}/${taskId}`, task);
  }

  deleteTask(taskId: number, currentUserId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${taskId}/${currentUserId}`);
  }

  completeTask(taskId: number): Observable<Task> {
    return this.http.post<Task>(`${this.baseUrl}/${taskId}/complete`, {});
  }
}
