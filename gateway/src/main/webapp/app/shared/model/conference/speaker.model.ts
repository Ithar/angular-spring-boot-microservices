import { ISession } from 'app/shared/model/conference/session.model';

export interface ISpeaker {
  id?: number;
  firstName?: string;
  lastName?: string;
  boi?: any;
  email?: string;
  twitter?: string;
  sessions?: ISession[];
}

export class Speaker implements ISpeaker {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public boi?: any,
    public email?: string,
    public twitter?: string,
    public sessions?: ISession[]
  ) {}
}
