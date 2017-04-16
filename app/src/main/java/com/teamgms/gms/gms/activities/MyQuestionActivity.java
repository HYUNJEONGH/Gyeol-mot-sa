package com.teamgms.gms.gms.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.teamgms.gms.gms.BaseActivity;
import com.teamgms.gms.gms.R;
import com.teamgms.gms.gms.adapters.MyQuestionAdapter;
import com.teamgms.gms.gms.models.Question;
import com.teamgms.gms.gms.utils.AccountUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;

public class MyQuestionActivity extends BaseActivity {

    Realm realm;
    RealmResults<Question> questions;
    Unbinder unbinder;
    @BindView(R.id.rvMyQuestion)
    RecyclerView rvMyQuestion;
    private LinearLayoutManager mLinearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_question);
        unbinder = ButterKnife.bind(this);

        AccountUtils utils = new AccountUtils();
        final String uid = utils.getFirebaseUid(MyQuestionActivity.this);
//        //TODO 잘못된 접근, 로그인 되지 않음, user not login이거나 유효하지 않은 접근 상태이다.
        if(uid == null) {
            goToLogin(this);
//            //TODO  Login이후 처리

        }

        Realm.init(this);

        //create realm instance
        realm = Realm.getDefaultInstance();

        /* FOR TEST, question 객체에 입력 */
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                final Question question = realm.createObject(Question.class);

                if (uid != null && !uid.equals("")) {
                    question.setUserId(uid);
                }
                question.setQuestion("test question");
                question.setChoice1("test 1");
                question.setChoice2("test 2");
                question.setChoice3("test 3");
                question.setChoice4("test 4");
                question.setNum(0L);
            }
        });

        //먼저 퀘스천이 렘에 저장, 그 후 읽기작업을 통해 자신이 올린 질문을 읽어오고 리스트로 뿌려쥼!
        //그후 에 삭제기능 정도가 추가가 될것 같다.

        /*Transaction을 관리해줌
        * 트랜잭션 블록
        *
        * */
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //find all
                questions = realm.where(Question.class)
                                .findAll();
            }
        });

        //size가 0이면 질의에 맞는 결과값이 없는것 이다,
        if(questions.size() == 0) {
            //TODO show empty view
        }

        Log.d("question", questions.size()+"");

        //setLayoutManager 반드시 해줘야함!
        mLinearLayoutManager = new LinearLayoutManager(this);
        rvMyQuestion.setLayoutManager(mLinearLayoutManager);
        rvMyQuestion.setAdapter(new MyQuestionAdapter(questions));
    }

    /*
    * It is good practice to null the reference from the view to the adapter when it is no longer needed.
    * Because the <code>RealmRecyclerViewAdapter</code> registers itself as a <code>RealmResult.ChangeListener</code>
    * the view may still be reachable if anybody is still holding a reference to the <code>RealmResult>.
    */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        //TODO NULLPOINTEREXCEPTION
//        rvMyQuestion.setAdapter(null);
        realm.close();
    }
}
