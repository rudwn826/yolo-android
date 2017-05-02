package kr.co.triggers.yolo.layer.entry.fragment.fiesta;

import kr.co.triggers.yolo.domain.Fiesta;

public interface OnFiestaChangedListener {

    void onFiestaChange(float position);
    void onFiestaChanged(Fiesta fiesta);
}
