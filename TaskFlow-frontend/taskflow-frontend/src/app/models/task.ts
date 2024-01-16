

export interface Task {
    id: number;
    title: string;
    description: string;
    priority: string;
    startDate: string;
    dueDate: string;
    createdBy: number;
    assignedTo: number;
    status: string;
    tagIds: [1,2];

}
