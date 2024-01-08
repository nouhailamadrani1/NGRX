export interface Task {
    id: number;
    title: string;
    description: string;
    startDate: string;
    endDate: string;
    status: string;
    // createdBy: User;
    // assignee: User;
    taskTags: TaskTag[];
  }
  
  export interface User {

  }
  
  export interface TaskTag {
   
  }
  