import { Task } from "./task";
import { User } from "./user";

export interface TokenDemand {
    id: number;
    demandDate: string;
    status: string;
    type: string;
    user: User;
    task: Task;
}
