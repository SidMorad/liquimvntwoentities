import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LiquimvntwoentitiesTestModule } from '../../../test.module';
import { TwoUpdateComponent } from 'app/entities/two/two-update.component';
import { TwoService } from 'app/entities/two/two.service';
import { Two } from 'app/shared/model/two.model';

describe('Component Tests', () => {
  describe('Two Management Update Component', () => {
    let comp: TwoUpdateComponent;
    let fixture: ComponentFixture<TwoUpdateComponent>;
    let service: TwoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LiquimvntwoentitiesTestModule],
        declarations: [TwoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TwoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TwoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TwoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Two(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Two();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
