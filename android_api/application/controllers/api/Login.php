<?php
	use Restserver\Libraries\REST_Controller;

	defined('BASEPATH') OR exit('No direct script access allowed');
	
	require APPPATH . 'libraries/REST_Controller.php';
	require APPPATH . 'libraries/Format.php';

	class Login extends REST_Controller{

		public function __construct()
		{
			parent::__construct();
			$this->load->model('Mahasiswa_model','mahasiswa');
		}

		public function index_post(){
			$email = $this->post('Email');
			$password = $this->post('Password');
			$mahasiswa = $this->mahasiswa->getUserLogin($email,$password);
			if($mahasiswa>0){
				$this->response([
					'status' => 200,
					'message' => "Login successful",
					'data' => $mahasiswa
                ], REST_Controller::HTTP_OK ); 
			}
			else{
				$this->response([
					'status' => 200,
					'message' => "",
					'result' => null
                ], REST_Controller::HTTP_NOT_FOUND);
			}
		}

		public function index_login(){
			
		}

		// public function index_post(){
		// 	$mahasiswa
		// }
	}

?>
