import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { SpeakerComponent } from 'app/entities/conference/speaker/speaker.component';
import { SpeakerService } from 'app/entities/conference/speaker/speaker.service';
import { Speaker } from 'app/shared/model/conference/speaker.model';

describe('Component Tests', () => {
  describe('Speaker Management Component', () => {
    let comp: SpeakerComponent;
    let fixture: ComponentFixture<SpeakerComponent>;
    let service: SpeakerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [SpeakerComponent],
      })
        .overrideTemplate(SpeakerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpeakerComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpeakerService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Speaker(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.speakers && comp.speakers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
