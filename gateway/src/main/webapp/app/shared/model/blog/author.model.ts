import { IBlogpost } from 'app/shared/model/blog/blogpost.model';

export interface IAuthor {
  id?: number;
  firstName?: string;
  lastName?: string;
  boi?: any;
  posts?: IBlogpost[];
}

export class Author implements IAuthor {
  constructor(public id?: number, public firstName?: string, public lastName?: string, public boi?: any, public posts?: IBlogpost[]) {}
}
