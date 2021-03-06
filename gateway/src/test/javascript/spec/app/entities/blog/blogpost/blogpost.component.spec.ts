import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../../test.module';
import { BlogpostComponent } from 'app/entities/blog/blogpost/blogpost.component';
import { BlogpostService } from 'app/entities/blog/blogpost/blogpost.service';
import { Blogpost } from 'app/shared/model/blog/blogpost.model';

describe('Component Tests', () => {
  describe('Blogpost Management Component', () => {
    let comp: BlogpostComponent;
    let fixture: ComponentFixture<BlogpostComponent>;
    let service: BlogpostService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [BlogpostComponent],
      })
        .overrideTemplate(BlogpostComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BlogpostComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BlogpostService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Blogpost(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.blogposts && comp.blogposts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
