import { Tag } from "./tag";
import { TokenDemand } from "./token-demand";
import { User } from "./user";

export interface Task {
    id: number;
    title: string;
    description: string;
    priority: string;
    startDate: string;
    dueDate: string;
    createdBy: User;
    assignedTo: User;
    status: string;
    tags: Tag[];
    tokenDemands: TokenDemand[];
}
