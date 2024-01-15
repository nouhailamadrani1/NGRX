import { Task } from "./task";

export interface Tag {
    id: number;
    name: string;
    tasks: Task[];
}
