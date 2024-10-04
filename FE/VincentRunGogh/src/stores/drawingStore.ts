import { LatLng } from 'leaflet';
import L from 'leaflet';

import { writable } from 'svelte/store';
import type { Writable } from 'svelte/store';

interface Position {
  lat: number;
  lng: number;
}
interface PositionData {
  latlng: L.LatLng;
  time: number;
  speed?: number;
}
export const isLockScreen = writable<boolean>(false);
export const isPause = writable(false);
export const totalDistance = writable(0);
export const currentPace = writable('00:00');
export const elapsedTime = writable(0);
export const posList = writable([]);
export const route = writable([]);

interface DrawingInfo {
  title: string;
  drawingPositionList?: Position[];
  routePositionList?: Position[];
  routeId?: string;
  drawingId?: string;
  endInfo?: Object;
}

// 초기 상태 정의
const initialState: DrawingInfo = {
  title: '',
  drawingPositionList: [],
  routePositionList: [],
};

// drawingStore를 생성하고 초기 상태를 할당
export const drawingStore: Writable<DrawingInfo> = writable(initialState);

export function updateDrawingInfo(data: Partial<DrawingInfo>): void {
  drawingStore.set({
    title: data.title || '',
    drawingPositionList: data.drawingPositionList || [],
    routePositionList: data.routePositionList || [],
    drawingId: data.drawingId && data.drawingId,
    routeId: data.routeId,
  });
}

export function setDrawingPos(data: Object): void {
  drawingStore.update((current) => {
    current.endInfo = data;
    return current;
  });
}

// 경과 시간과 좌표 목록을 받아 총 이동 거리와 현재 페이스를 업데이트하는 함수
export function updateDistanceAndSpeed(posList: PositionData[]) {
  totalDistance.update((totalDistance) => {
    if (posList.length > 1) {
      const lastPosData = posList[posList.length - 2];
      const newPosData = posList[posList.length - 1];
      const lastPos = lastPosData.latlng;
      const newPos = newPosData.latlng;

      const distance = lastPos.distanceTo(newPos) / 1000; // km로 변환
      const timeDiff = (newPosData.time - lastPosData.time) / 1000; // 밀리초를 초로 변환

      if (timeDiff > 0 && distance > 0) {
        console.log(timeDiff);
        console.log(distance);
        const speed = distance / (timeDiff / 3600); // 속도 (km/h)
        const paceSeconds = 3600 / speed; // 1km 달리는데 필요한 시간 (초)
        const paceMinutes = Math.floor(paceSeconds / 60); // 분
        const remainingSeconds = Math.round(paceSeconds % 60); // 초

        // 콘솔 로그로 확인
        console.log(
          `Pace: ${paceMinutes}m ${remainingSeconds < 10 ? '0' : ''}${remainingSeconds}s`
        );

        // 현재 페이스 업데이트
        currentPace.set(`${paceMinutes}:${remainingSeconds < 10 ? '0' : ''}${remainingSeconds}`);
      }

      // 총 이동 거리 업데이트
      return totalDistance + distance;
    }
    return totalDistance;
  });
}
export function resetDrawingStore(): void {
  drawingStore.set({
    ...initialState,
  });
  // 다른 스토어들도 초기화
  isLockScreen.set(false);
  isPause.set(false);
  totalDistance.set(0);
  currentPace.set('');
  elapsedTime.set(0);
  posList.set([]);
  route.set([]);
}
