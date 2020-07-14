import { Moment } from 'moment';
import { ISpeaker } from 'app/shared/model/conference/speaker.model';

export interface ISession {
  id?: number;
  title?: string;
  description?: string;
  startTime?: Moment;
  endTime?: Moment;
  speakers?: ISpeaker[];
}

export class Session implements ISession {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public startTime?: Moment,
    public endTime?: Moment,
    public speakers?: ISpeaker[]
  ) {}
}
