import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LiquimvntwoentitiesTestModule } from '../../../test.module';
import { TwoDetailComponent } from 'app/entities/two/two-detail.component';
import { Two } from 'app/shared/model/two.model';

describe('Component Tests', () => {
  describe('Two Management Detail Component', () => {
    let comp: TwoDetailComponent;
    let fixture: ComponentFixture<TwoDetailComponent>;
    const route = ({ data: of({ two: new Two(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LiquimvntwoentitiesTestModule],
        declarations: [TwoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TwoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TwoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load two on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.two).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
