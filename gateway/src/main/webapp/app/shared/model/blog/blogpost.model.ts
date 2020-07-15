import { Moment } from 'moment';
import { IAuthor } from 'app/shared/model/blog/author.model';

export interface IBlogpost {
  id?: number;
  title?: string;
  intro?: string;
  blog?: any;
  created?: Moment;
  author?: IAuthor;
}

export class Blogpost implements IBlogpost {
  constructor(
    public id?: number,
    public title?: string,
    public intro?: string,
    public blog?: any,
    public created?: Moment,
    public author?: IAuthor
  ) {}
}
